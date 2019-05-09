//
//  LoginVM.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/9.
//  Copyright © 2019 Brian. All rights reserved.
//


import RxSwift
import RxCocoa

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
class LoginVM : TWBViewModel ,LoginVMInputs , LoginVMOutputs{
    
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
        
    }
    
    func setUsername(username: String) {
        
    }
    
    func setPassword(password: String) {
        
    }
    
    

}
