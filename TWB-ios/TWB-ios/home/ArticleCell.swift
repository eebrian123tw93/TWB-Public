//
//  ArticleCell.swift
//  TWB-ios
//
//  Created by Brian on 2019/6/5.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit

class ArticleCell: UITableViewCell {

   
    @IBOutlet weak var userId: UILabel!
    
    @IBOutlet weak var title: UILabel!
    @IBOutlet weak var preview: UILabel!
    @IBOutlet weak var likeCount: UILabel!
    
    @IBOutlet weak var views: UILabel!
    @IBOutlet weak var commentCount: UILabel!
    
    
    @IBOutlet var myImage: UIImageView!
}
