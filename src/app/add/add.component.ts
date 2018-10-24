import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase } from 'angularfire2/database';
import {Room} from '../Room';
import { Router } from '@angular/router';
import {ActivatedRoute} from "@angular/router"
@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {
  mode: string;
  room: Room;
  constructor(private db: AngularFireDatabase,private route: ActivatedRoute,private router: Router) {

  }
  ngOnInit() {
    this.route.params.subscribe((params) => {
      if (params.mode == 'add') {
        this.mode = "New";
        this.room = < Room > {};
        }else if(params.mode == 'edit')  {
        this.mode = "Edit";
        this.room = < Room > {};
        var object = this.db.object(params.id)
        object.snapshotChanges().subscribe(action => {
          this.room.RoomName=action.payload.val()["RoomName"]
          this.room.Descrption=action.payload.val()["Descrption"]
        }); 
      }
    });
  }
public Done(){
  var roo = this.db.object(this.room.RoomName);
  roo.set(this.room); 
  this.router.navigateByUrl('');
  }
}
