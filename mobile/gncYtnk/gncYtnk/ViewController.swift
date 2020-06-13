//
//  ViewController.swift
//  gncYtnk
//
//  Created by Can Babaoğlu on 12.06.2020.
//  Copyright © 2020 Can Babaoğlu. All rights reserved.
//

import UIKit
import Alamofire
import SwiftyJSON


class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, UISearchBarDelegate {

    
    var BookArray: [BookObject] = []
    var tempBook = BookObject()
    let cellReuseIdentifier = "cell"
    
    
    var checkBoxArray: [Int] = [0,0,0,0,0]
    
    
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet var searchBar: UISearchBar!
    

    @IBAction func btn_boxName(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        if (sender.isSelected == true){
            checkBoxArray[0] = 1
            
        }else{
            checkBoxArray[0] = 0
        }
        
        
    }
    @IBAction func btn_boxYear(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        if (sender.isSelected == true){
            checkBoxArray[1] = 1
            
        }else{
            checkBoxArray[1] = 0
        }
    }
    @IBAction func btn_boxPublisher(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        if (sender.isSelected == true){
            checkBoxArray[2] = 1
            
        }else{
            checkBoxArray[2] = 0
        }
    }
    @IBAction func btn_boxAuthor(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        if (sender.isSelected == true){
            checkBoxArray[3] = 1
            
        }else{
            checkBoxArray[3] = 0
        }
    }
    @IBAction func btn_boxAll(sender: UIButton) {
        sender.isSelected = !sender.isSelected
        if (sender.isSelected == true){
            checkBoxArray[4] = 1
            
        }else{
            checkBoxArray[4] = 0
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // http://localhost:8080/bookOperation/search/all/harry
        
        //fetch from api
                
        // storyboard u override ediyor cell i bozuyor böyle bağlama !!!
        //    self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: cellReuseIdentifier)

        // delegates
        tableView.delegate = self
        tableView.dataSource = self
        searchBar.delegate = self
        
        // tahmin vs ekledik autolayoutu bozma!!
        tableView.estimatedRowHeight = 44.0
        tableView.rowHeight = UITableView.automaticDimension
                     
    }
    
    
    func getRequestFromLibrary(_ searchParam: String, _ searchType: String){ // alamofire request
                
        let reqUrl = "http://localhost:8080/bookOperation/search/" + searchType + "/" + searchParam
        
        Alamofire.request(reqUrl).responseJSON { response in
            
            if (response.result.isSuccess){
                let deneme = response.result.value as! NSDictionary
                let deneRespObj: NSArray = deneme.object(forKey: "responseObject") as! NSArray
                let deneDesc: String = deneme.object(forKey: "responseDescription") as! String
                       
         
                for i in 0..<deneRespObj.count{
                    
                    self.tempBook.Name = (deneRespObj[i] as AnyObject).object(forKey: "name") as! String
                    self.tempBook.Publisher = (deneRespObj[i] as AnyObject).object(forKey: "publisher") as! String
                    self.tempBook.Author = (deneRespObj[i] as AnyObject).object(forKey: "writer") as! String
                    self.tempBook.Year = (deneRespObj[i] as AnyObject).object(forKey: "year") as! String
                
                    self.BookArray.insert(self.tempBook, at: i)
                }
                     
            }else {
                print("Error: \(String(describing: response.result.error))")
            }
                 
            self.tableView.reloadData()
            self.searchBar.resignFirstResponder()
        }
        
    }
    

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.BookArray.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        
        
        let cell: MyCustomCell = self.tableView.dequeueReusableCell(withIdentifier: cellReuseIdentifier ) as! MyCustomCell
        
        cell.myCellLabel.text = "Kitap: " + self.BookArray[indexPath.row].Name! + " || Yazar: " + self.BookArray[indexPath.row].Author! + " || Yayınevi: " + self.BookArray[indexPath.row].Publisher! + " || Yıl: " + self.BookArray[indexPath.row].Year!
        
        cell.myCellLabel.font = cell.myCellLabel.font.withSize(14)
        return cell
    }

    // zaman kalırsa yap
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("You tapped cell number \(indexPath.row).")
    }

    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
       // print("searchText \(String(describing: searchBar.text))")
        self.BookArray.removeAll() // to clear tableview for another search
        tableView.reloadData()
        
        if(searchBar.text! != "" ){ // to avoid alamofire crash
        getRequestFromLibrary(searchBar.text!, checkBoxController())
        }
                
    }
    
    
    func checkBoxController() -> String{ // search Type ı ayarla
        
        var checkCount: Int = 0
        
        for i in 0..<checkBoxArray.count{
            checkCount = checkCount + checkBoxArray[i]
        }
        
        if(checkCount > 1 || checkCount == 0){ // birden fazla seçim yapıldıysa yada hiç yapılmadıysa: ALL
            return "all"
            
        }else{ // tek seçim varsa onun ne olduğunu ayır
            
            if(checkBoxArray[0] == 1){
                return "name"
                
            }else if(checkBoxArray[1] == 1){
                return "year"
                
            }else if(checkBoxArray[2] == 1){
                return "publisher"
                
            }else if(checkBoxArray[3] == 1){
                return "writer"
                
            }else{
                return "all"
                
            }
            
        }
    }
    
}

