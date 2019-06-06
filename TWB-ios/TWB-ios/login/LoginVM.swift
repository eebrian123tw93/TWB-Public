//
//  LoginVM.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/9.
//  Copyright © 2019 Brian. All rights reserved.
//


import RxSwift
import RxCocoa
import RxAlamofire

protocol LoginVMInputs {
    
    
    func login()
    func setUsername(username:String)
    func setPassword( password: String )
    

}

protocol LoginVMOutputs {
   
    var errorOccur: Observable<Error> {get}
    
    var loading: Observable<Bool> {get}
    
    var signInCompleted: Observable<Bool> {get}
   
}
protocol LoginVMType {
    var inputs: LoginVMInputs {get}
    var outputs: LoginVMOutputs {get}
    func viewDidLoad()
}
class LoginVM : TWBViewModel ,LoginVMInputs , LoginVMOutputs,LoginVMType{
    
    //BehaviorRelay有初始值，并且重复发射最晚一个元素给订阅者。
    private var _username = BehaviorRelay<String>(value: "")
    private var _password = BehaviorRelay<String>(value: "")
    
    
    var inputs: LoginVMInputs { return self }
    var outputs: LoginVMOutputs { return self }
    
    
    // outputs
    private var _loading = ActivityIndicator()
    private var _errorOccur = ErrorTracker()
    
    //PublishSubject初始化为空，只发射最新的元素给订阅者。
    private var _signInCompleted = PublishSubject<Bool>()
    
    //PublishSubject：初始化为空，只发射最新的元素给订阅者。
    //BehaviorSubject：有初始值，并且重复发射最晚一个元素给订阅者。
    //ReplaySubject：存在一个缓存区，重复发射符合缓存个数的元素给新的订阅者。
    //Variable：是BehaviorSubject的包装。
    //AsyncSubject: 只有在接收到 .completed事件时，发射最后一个 .next 事件。这个类型 Subject 很少使用。
    //PublishRelay 和 BehaviorRelay：包装相关的 Subject，只接受 .next 事件
    
  
    
    var loading: Observable<Bool> { return _loading.asObservable() }
    var errorOccur: Observable<Error> { return _errorOccur.asObservable() }
    var signInCompleted: Observable<Bool> { return _signInCompleted.asObservable() }
    
    

    
    
    func login() {
        
        let observer: AnyObserver<(HTTPURLResponse,Data)> = AnyObserver { [unowned self] (event) in
            switch event {
                
            case .next((let response,let data)):
                if 200 ..< 300 ~= response.statusCode {
                        let str = String(data: data, encoding: String.Encoding.utf8)
                        print("登入成功", str ?? "")
                        self._signInCompleted.onNext(true)
                    }else{
                        let str = String(data: data, encoding: String.Encoding.utf8)
                        self._signInCompleted.onNext(false)
                        print("登入失敗", str ?? "")
                }
                break
            case .error(let error):
                self._errorOccur.raiseError(error)
                break
            default:
                break
            }
            
            
        }
        
        ShuoApiService.instance
            .login(user: User(userId: _username.value, password:_password.value))
            .trackActivity(self._loading)
            .subscribe(observer)
            .disposed(by: disposeBag)
        
//        ShuoApiService.instance.
        
        
    }
    
    func setUsername(username: String) {
        _username.accept(username);
        // _username.value  拿PublishSubject的值
        //_username.accept(username);  設定PublishSubject的值
    }
    
    func setPassword(password: String) {
        _password.accept(password)
    }
    func viewDidLoad() {
        
    }
    
    

}
