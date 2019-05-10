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

    @IBOutlet weak var usernameTextField: UITextField!
   
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
//        self.view.backgroundColor=UIColor.gray
        
        let usernameVaild=usernameTextField.rx.text.orEmpty.map{$0.count>0}.share(replay: 1)
        let passwordVaild=passwordTextField.rx.text.orEmpty.map({$0.count>0}).share(replay: 1)
        
        
        let bothVaild=Observable.combineLatest(usernameVaild,passwordVaild){$0&&$1}.share(replay: 1)
        
        bothVaild.bind(to: loginButton.rx.isEnabled).disposed(by: disposeBag)
        loginButton.rx.tap.subscribe(onNext: {[weak self] _ in self?.showAlert()}).disposed(by: disposeBag)
        // Do any additional setup after loading the view.
    }
    func showAlert()  {
        let alertView=UIAlertController(title: "Login", message: "loading", preferredStyle: .alert)
        let okAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alertView.addAction(okAction)
        present(alertView, animated: true, completion: nil)

    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
