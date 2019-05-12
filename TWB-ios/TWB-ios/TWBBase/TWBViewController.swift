//
//  TWBViewController.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/10.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import RxSwift
import RxCocoa
class TWBViewController: UIViewController {
    var disposeBag = DisposeBag()

    override func viewDidLoad() {
        super.viewDidLoad()
//        UIColor.init(red: <#T##CGFloat#>, green: <#T##CGFloat#>, blue: <#T##CGFloat#>, alpha: <#T##CGFloat#>)
        
        self.view.backgroundColor=UIColor.init(red:249.0/255.0, green: 249.0/255.0, blue: 249.0/255.0, alpha: 1)
        let myView = UITapGestureRecognizer(target: self, action: #selector(closeKeyboard(_:)))
        self.view.addGestureRecognizer(myView)
        // Do any additional setup after loading the view.
    }
    
    @objc func closeKeyboard(_ sender:UITapGestureRecognizer){
        self.view.endEditing(true)
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

