import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {User} from "../../models/User";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  @Input()
  user: User;

  @Output()
  edit: EventEmitter<User> = new EventEmitter();

  @Output()
  remove: EventEmitter<User> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  onEdit() {
    this.edit.emit(this.user);
  }

  onRemove() {
    this.remove.emit(this.user);
  }

}
