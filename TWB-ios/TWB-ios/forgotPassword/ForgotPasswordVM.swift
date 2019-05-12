//
//  ForgotPasswordVM.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/10.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
protocol ForgotPasswordInputs {
    func sendEmail()
    func  setEmail(email:String)
}

protocol ForgotPasswordOutputs {
    var errorOccur: Observable<Error> {get}
    
    var loading: Observable<Bool> {get}
    
    var sendSuccessed:Observable<Bool>{get}
}

protocol ForgotPasswordVMType {
    var inputs: ForgotPasswordInputs {get}
    var outputs: ForgotPasswordOutputs {get}
    func viewDidLoad()
}


class ForgotPasswordVM:TWBViewModel,ForgotPasswordVMType,ForgotPasswordInputs,ForgotPasswordOutputs {
    var inputs: ForgotPasswordInputs{return self}
    
    var outputs: ForgotPasswordOutputs{return self}
    
    private var _email = BehaviorRelay<String>(value: "")
    
    // outputs
    private var _loading = ActivityIndicator()
    private var _errorOccur = ErrorTracker()
    private var _successed = PublishSubject<Bool>()
    
    var errorOccur: Observable<Error>{return _errorOccur.asObservable()}
    
    var loading: Observable<Bool>{return _loading.asObservable()}
    
    var sendSuccessed: Observable<Bool>{return _successed.asObserver()}
    
    func sendEmail() {
        let observer: AnyObserver<(HTTPURLResponse,Data)> = AnyObserver { [unowned self] (event) in
            switch event {
                
            case .next((let response,let data)):
                if 200 ..< 300 ~= response.statusCode {
                  
                   
                    self._successed.onNext(true)
                }else{
                    let str = String(data: data, encoding: String.Encoding.utf8)
                    
                    self._successed.onNext(false)
                    
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
            .forgotPassword(email: _email.value)
            .trackActivity(self._loading)
            .subscribe(observer)
            .disposed(by: disposeBag)
        
    }
    
    func setEmail(email: String) {
        _email.accept(email)
    }
    
    func viewDidLoad() {
        
    }
    

}
