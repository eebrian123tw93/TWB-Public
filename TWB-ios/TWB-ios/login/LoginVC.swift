//
//  LoginVCViewController.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/9.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa

class LoginVC: TWBViewController {
    var viewModel: LoginVMType = LoginVM()
    @IBOutlet weak var usernameTextField: UITextField!
   
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    @IBOutlet weak var forgotPasswordButton: UIButton!
    
    @IBOutlet weak var clearButton: UIButton!
    
    @IBOutlet weak var registerButton: UIButton!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        self.view.backgroundColor=UIColor.gray
        
//        let usernameVaild=usernameTextField.rx.text.orEmpty.map{$0.count>0}
//            .share(replay: 1)
//
//        let passwordVaild=passwordTextField.rx.text.orEmpty.map({$0.count>0})
//            .share(replay: 1)
//
//
//        let bothVaild=Observable.combineLatest(usernameVaild,passwordVaild){$0&&$1}
//            .share(replay: 1)
//
//
//
//        bothVaild.bind(to: loginButton.rx.isEnabled)
//            .disposed(by: disposeBag)

        inputBindings()
       
    }

    func inputBindings()  {
        forgotPasswordButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext: {[weak self]_ in self?.forgotPassword()} ).disposed(by: disposeBag)
        clearButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext:{[weak self]_ in self?.clearTextField()}).disposed(by: disposeBag)
        registerButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext:{[weak self ]_ in self?.register()}).disposed(by: disposeBag)
        
        loginButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext: {[unowned self] _ in
            
            guard (self.usernameTextField.text?.count)! > 0 else {
                Toast.showToast(controller: self, message: "請輸入帳號", seconds: 1)
                return
            }
            guard (self.passwordTextField.text?.count)! > 0 else {
                Toast.showToast(controller: self, message: "請輸入密碼", seconds: 1)
                return
            }
            
            self.usernameTextField.resignFirstResponder()
            self.passwordTextField.resignFirstResponder()
            
            
            Toast.showToast(controller: self, message: "Login", seconds: 1)
            self.viewModel.inputs.login()
            
        }).disposed(by: disposeBag)
        
        self.usernameTextField.rx.text.orEmpty.asObservable().subscribe(onNext:{(username)in
            self.viewModel.inputs.setUsername(username: username)
        }).disposed(by: disposeBag)
        
        self.passwordTextField.rx.text.orEmpty.asObservable().subscribe(onNext:{(password)in
            self.viewModel.inputs.setPassword(password: password)
        }).disposed(by: disposeBag)
        
    }
    
    func clearTextField()  {
        usernameTextField.text?.removeAll()
        passwordTextField.text?.removeAll()
    }
    
    func forgotPassword(){
        let forgotPasswordVC =  UIStoryboard(name: "Forgot", bundle: nil).instantiateViewController(withIdentifier: "ForgotPasswordVC") as! ForgotPasswordVC
        present(forgotPasswordVC, animated: true, completion: nil)
//        self.navigationController?.pushViewController(forgotPasswordVC, animated: true)
        
    }
    
    
    func register()  {
        Toast.showToast(controller: self, message: "Register", seconds: 1)
    }

  

}
