//
//  User.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/11.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import SwiftyJSON
import Alamofire

class User {
    private var userId:String
    private var password:String
    private var email:String?
    
   
    init(userId:String,password:String) {
        self.userId=userId
        self.password=password
        
    }
    
    func authKey() -> String {
        let original = userId+":"+password
        return "Basic "+(original.data(using: .utf8)?.base64EncodedString())!
    }
    func toJson() -> Parameters {
        return [
            "userId":self.userId  ,
            "password":password  ,
            "email":email ?? "null" ,
        ]
    }
}
