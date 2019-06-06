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
import SVProgressHUD
import Toast_Swift

class LoginVC: TWBViewController {
    var viewModel: LoginVMType = LoginVM()
    @IBOutlet  var usernameTextField: UITextField!
   
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    @IBOutlet weak var forgotPasswordButton: UIButton!
    
    @IBOutlet weak var clearButton: UIButton!
    
    @IBOutlet weak var registerButton: UIButton!
    
    @IBOutlet weak var cardView: CardView!
    
   
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title="登入"
        self.inputBindings()
        self.outputBindings()
       
    }
    override func viewDidAppear(_ animated: Bool) {
        
    }

    func inputBindings()  {
        forgotPasswordButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext: {[weak self]_ in self?.forgotPassword()} ).disposed(by: disposeBag)
        clearButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext:{[weak self]_ in self?.clearTextField()}).disposed(by: disposeBag)
        registerButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext:{[weak self ]_ in self?.register()}).disposed(by: disposeBag)
        
        loginButton.rx.tap.throttle(1, scheduler: MainScheduler.instance).subscribe(onNext: {[unowned self] _ in
            
            guard (self.usernameTextField.text?.count)! > 0 else {
                self.view.makeToast("請輸入帳號")
               
                return
            }
            guard (self.passwordTextField.text?.count)! > 0 else {
                self.view.makeToast("請輸入密碼")
                
                return
            }
            
            self.usernameTextField.resignFirstResponder()
            self.passwordTextField.resignFirstResponder()
            
            
            //Toast.showToast(controller: self, message: "Login", seconds: 1)
            self.viewModel.inputs.login()
            
        }).disposed(by: disposeBag)
        
       
       /* text是观察的属性,orEmpty是检验text是否为nil如果为nil返回"",asDriver()是具体特殊属性的Observable,如果使用asObservable(),就要额外添加.observeOn(MainScheduler.instance)和.shareReplay(1),
         */
        
       
        self.usernameTextField.rx.text.orEmpty.asObservable().subscribe(onNext:{(username)in
            self.viewModel.inputs.setUsername(username: username)
        }).disposed(by: disposeBag)
        
        self.passwordTextField.rx.text.orEmpty.asObservable().subscribe(onNext:{(password)in
            self.viewModel.inputs.setPassword(password: password)
        }).disposed(by: disposeBag)
        
       
        
    }
    func outputBindings()  {
       
        self.viewModel.outputs.signInCompleted.subscribe(onNext:{
            (success) in
            if success {
                self.view.makeToast("登入成功")
            }else{
                self.view.makeToast("登入失敗")
            }
        }).disposed(by: disposeBag)
        
        self.viewModel.outputs.loading.subscribe(onNext:{
            (loading)in
            if loading{
                SVProgressHUD.show()
            }else{
                SVProgressHUD.dismiss()
            }
        }).disposed(by: disposeBag)
        
        self.viewModel.outputs.errorOccur.subscribe(onNext:{
            (error)in
            
            
        }).disposed(by: disposeBag)
    }
    
    func clearTextField()  {
        usernameTextField.text?.removeAll()
        passwordTextField.text?.removeAll()
    }
    
    func forgotPassword(){
        let forgotPasswordVC =  UIStoryboard(name: "Forgot", bundle: nil).instantiateViewController(withIdentifier: "ForgotPasswordVC") as! ForgotPasswordVC
        self.navigationController?.pushViewController(forgotPasswordVC, animated: true)
        
//        self.navigationController?.pushViewController(forgotPasswordVC, animated: true)
        
    }
    
    
    func register()  {
        self.view.makeToast("Register")
    }

  

}
