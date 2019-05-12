//
//  ForgotPasswordVC.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/10.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
import SVProgressHUD

class ForgotPasswordVC: TWBViewController {
    var viewModel:ForgotPasswordVMType=ForgotPasswordVM()
    @IBOutlet weak var sendButton: UIButton!
    @IBOutlet weak var emailTextField: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title="忘記密碼"
        self.inputBindings()
        self.outputBindings()
    }
    func inputBindings()  {
        sendButton.rx.tap.throttle(1,  scheduler: MainScheduler.instance).subscribe(onNext:{
            [unowned self] _ in
            self.emailTextField.resignFirstResponder()
            guard (self.emailTextField.text?.count)! > 0 else {
                self.view.makeToast("請輸入信箱")
                
                return
            }
            
            self.viewModel.inputs.sendEmail()
        }).disposed(by: disposeBag)
        
        self.emailTextField.rx.text.orEmpty.asObservable().subscribe(onNext:{(email)in
            self.viewModel.inputs.setEmail(email: email)
        }).disposed(by: disposeBag)
    }
    func outputBindings()  {
        self.viewModel.outputs.sendSuccessed.subscribe(onNext:{
            [unowned self](success) in
            if success {
                if let email=self.emailTextField.text{
                    self.view.makeToast("請至" + email + "獲取密碼")
                }
              
                
                
            }else{
                self.view.makeToast("查無此email")
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
}
