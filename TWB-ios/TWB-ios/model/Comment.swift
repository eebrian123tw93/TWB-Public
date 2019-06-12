//
//  Comment.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/13.
//  Copyright © 2019 Brian. All rights reserved.
//

import Foundation
import Alamofire
struct Comment {
    var articleId:String
    var userId : String
    var comment :String
    var commentTime:Date
    
    func toJson() -> Parameters {
        return[:]
    }
}
