//
//  TabBarController.swift
//  TWB-ios
//
//  Created by Brian on 2019/5/9.
//  Copyright © 2019 Brian. All rights reserved.
//

import UIKit

class TabBarController: UITabBarController , UITabBarControllerDelegate {

    var subNC:[UIViewController]=[]
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let homeVC =  UIStoryboard(name: "Home", bundle: nil).instantiateViewController(withIdentifier: "HomeVC") as! HomeVC
        let home=UINavigationController(rootViewController: homeVC)
        home.tabBarItem.title="Home"
        home.tabBarItem.image=UIImage(named: "home.png")?.resized(to: CGSize(width: 30, height: 30))
        subNC.append(home)
        
        let search=UINavigationController(rootViewController: SearchVC())
        search.tabBarItem.title="Search"
        search.tabBarItem.image=UIImage(named: "search.png")?.resized(to: CGSize(width: 30, height: 30))
        
        
        let upload=UINavigationController(rootViewController: UploadVC())
        upload.tabBarItem.title="Upload"
        upload.tabBarItem.image=UIImage(named: "upload.png")?.resized(to: CGSize(width: 30, height: 30))
        
         let profileHomeVC =  UIStoryboard(name: "ProfileHome", bundle: nil).instantiateViewController(withIdentifier: "ProfileHomeVC") as! ProfileHomeVC
        let profileHome=UINavigationController(rootViewController: profileHomeVC)
        profileHome.tabBarItem.title="Profile"
        profileHome.tabBarItem.image=UIImage(named: "profile.png")?.resized(to: CGSize(width: 30, height: 30))
        
        subNC.append(home)
        subNC.append(search)
        subNC.append(upload)
        subNC.append(profileHome)
        

        self.delegate=self
        self.viewControllers=subNC
        self.tabBar.unselectedItemTintColor=Color.hexStringToUIColor(hex: "#008577")
        
        // Do any additional setup after loading the view.
    }
    
    func tabBarController(_ tabBarController: UITabBarController, didSelect viewController: UIViewController) {
        
//        if let firstVC = viewController as? FirstViewController {
//            firstVC.doSomeAction()
//        }
//
//        if viewController is FirstViewController {
//            print("First tab")
//        } else if viewController is SecondViewController {
//            print("Second tab")
//        }
    }
    
    // alternate method if you need the tab bar item
    override func tabBar(_ tabBar: UITabBar, didSelect item: UITabBarItem) {
        // ...
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
