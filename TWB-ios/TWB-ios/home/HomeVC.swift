//
//  HomeVC.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/9.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit
import LZViewPager
class HomeVC: TWBViewController,LZViewPagerDelegate, LZViewPagerDataSource {
    
    
    @IBOutlet weak var viewPager: LZViewPager!
    private var subVCs:[UIViewController]=[]
    func numberOfItems() -> Int {
        return subVCs.count
    }
    
    func controller(at index: Int) -> UIViewController {
        return subVCs[index]
    }
    
    func button(at index: Int) -> UIButton {
        let button = UIButton()
        button.setTitleColor(UIColor.black, for: .normal)
        button.titleLabel?.font = UIFont.systemFont(ofSize: 16)
        switch index {
        case 0:
            button.setImage(UIImage(named: "popular.png")?.resized(width: 30, height: 30), for: .normal)
            break;
        case 1:
            button.setImage(UIImage(named: "new_icon.png")?.resized(width: 30, height: 30), for: .normal)
            break
        default:
            break
        }
        return button
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        viewPager.dataSource = self
        viewPager.delegate = self
        viewPager.hostController = self
        
        self.navigationItem.title="首頁"
        
        let popularVC=ArticleListVC()
        popularVC.title="熱門"
        
        let newVC=ArticleListVC()
        newVC.title="最新"
        
        subVCs.append(popularVC)
        subVCs.append(newVC)
        
        viewPager.reload()
 
        // Do any additional setup after loading the view.
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
