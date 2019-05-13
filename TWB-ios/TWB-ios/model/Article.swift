//
//  Article.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/13.
//  Copyright © 2019 Brian. All rights reserved.
//

import Foundation
import Alamofire
struct  Article {
     var articleId:String
     var content:String
     var images:[String]
     var userId:String
     var title:String
     var points:Int64
     var views:Int64
     var likeStatus:Int64
     var createTime:Date
    
    func toJson() ->  Parameters{
        return [:]
    }
}
