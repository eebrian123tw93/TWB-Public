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
    
    var signInCompleted: Observable<Void> {get}
   
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
    
    private var _signInCompleted = PublishSubject<Void>()
    
    
    var loading: Observable<Bool> { return _loading.asObservable() }
    var errorOccur: Observable<Error> { return _errorOccur.asObservable() }
    var signInCompleted: Observable<Void> { return _signInCompleted.asObservable() }
    
    

    
    
    func login() {
        NSLog(_username.value)
        NSLog(_password.value)
        
//        let observer = AnyObserver<Data>(){
//            data in
//            let str = String(data: data, encoding: String.Encoding.utf8)
//            print("返回的数据是：", str ?? "")
//        }
        let observer: AnyObserver<Data> = AnyObserver { [weak self] (event) in
            switch event {
            case .next(let data):
                //收到发出的索引数后显示到label上
                let str = String(data: data, encoding: String.Encoding.utf8)
                print("返回的数据是：", str ?? "")
           
            default:
                break
            }
        }
        ShuoApiService.instance.test(observer: observer)
        
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
