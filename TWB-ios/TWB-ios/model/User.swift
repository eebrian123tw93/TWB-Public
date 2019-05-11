//
//  User.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/11.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit

class User {
    private var userId:String
    private var password:String
    private var email:String
    
   
    init(userId:String,password:String,email:String) {
        self.userId=userId
        self.password=password
        self.email=email
    }
    
    func authKey() -> String {
        let original = userId+":"+password
        return "Basic "+(original.data(using: .utf8)?.base64EncodedString())!
    }
}
