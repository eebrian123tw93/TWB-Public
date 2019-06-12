//
//  ProfileHomeVC.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/13.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import LZViewPager

class ProfileHomeVC: TWBViewController ,LZViewPagerDelegate, LZViewPagerDataSource{
    @IBOutlet weak var viewPager: LZViewPager!
    private var subControllers:[UIViewController] = []
    func numberOfItems() -> Int {
        return subControllers.count
    }
    
    func controller(at index: Int) -> UIViewController {
        return subControllers[index]
    }
    
    func button(at index: Int) -> UIButton {
        let button = UIButton()
        button.setTitleColor(UIColor.black, for: .normal)
        button.titleLabel?.font = UIFont.systemFont(ofSize: 16)
        switch index {
        case 0:
            button.setImage(UIImage(named: "user.png")?.resized(to: CGSize(width: 30, height: 30)), for: .normal)
            break;
        case 1:
            button.setImage(UIImage(named: "upload.png")?.resized(to: CGSize(width: 30, height: 30)), for: .normal)
            break
        default:
            break
        }
        return button
    }
    
    
    override func viewDidLoad() {
        self.navigationItem.title="簡介"
        viewPager.dataSource = self
        viewPager.delegate = self
        viewPager.hostController = self
        let vc1 = ProfileVC()
        let vc2 = UserPostHistoryVC()
        subControllers = [vc1, vc2 ]
        viewPager.reload()
    }

    /*
    // Only override draw() if you perform custom drawing.
    // An empty implementation adversely affects performance during animation.
    override func draw(_ rect: CGRect) {
        // Drawing code
    }
    */

}
