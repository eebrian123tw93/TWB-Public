//
//  Like.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/13.
//  Copyright © 2019 Brian. All rights reserved.
//

import Foundation
import Alamofire
struct Like {
    var articleId :String
    var userId:String
    var type:Type
    
    enum `Type`:Int{
        case LIKE = 1
        case DISLIKE = -1
    }
    
    func toJson() -> Parameters {
        return[:]
    }
}
