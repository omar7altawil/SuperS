import { Component, OnInit } from '@angular/core';
import { AngularFireDatabase } from 'angularfire2/database';
import {Room} from '../Room';
@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.scss']
})

export class DisplayComponent implements OnInit {
    rooms: Room[]=[];
    room: Room;

    constructor(private db: AngularFireDatabase) {

  }
  public delete(id){
    if (confirm("Are you sure you want to delete Room:" + id + '?')) {
      var name = this.db.list('/');
      name.remove(id);
      this.rooms.splice(this.rooms.indexOf(id), 1)
    }

  }
  ngOnInit() {
    var object = this.db.object('/')
    object.snapshotChanges().subscribe(action => {
      var array=action.payload.val();
      for (var property in array) {
        this.room = < Room > {};
        this.room.RoomName=action.payload.val()[property]["RoomName"]
        this.room.Descrption=action.payload.val()[property]["Descrption"]
        this.rooms.push(this.room)
        }
    }); 
  }
}
