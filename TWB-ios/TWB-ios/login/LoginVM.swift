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
    
    
    private var _username = BehaviorRelay<String>(value: "")
    private var _password = BehaviorRelay<String>(value: "")
    
    
    var inputs: LoginVMInputs { return self }
    var outputs: LoginVMOutputs { return self }
    
    
    // outputs
    private var _loading = ActivityIndicator()
    private var _errorOccur = ErrorTracker()
    
    private var _signInCompleted = PublishSubject<Bool>()
    
    
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
    }
    
    func setPassword(password: String) {
        _password.accept(password)
    }
    func viewDidLoad() {
        
    }
    
    

}
