//
//  Toast.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/10.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit

class Toast {
     static func showToast(controller: UIViewController, message : String, seconds: Double) {
    
        let alert = UIAlertController(title: nil, message: message, preferredStyle: .alert)
        alert.view.backgroundColor = UIColor.black
        alert.view.alpha = 0.6
        alert.view.layer.cornerRadius = 15
        
        controller.present(alert, animated: true)
        
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + seconds) {
            alert.dismiss(animated: true)
        }
    }
}
