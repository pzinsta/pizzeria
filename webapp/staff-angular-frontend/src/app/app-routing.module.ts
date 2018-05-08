import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "./components/user-list/user-list.component";
import {AccountListComponent} from "./components/account-list/account-list.component";
import {EditUserComponent} from "./components/edit-user/edit-user.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent
  },
  {
    path: 'users',
    component: UserListComponent
  },
  {
    path: 'accounts',
    component: AccountListComponent
  },
  {
    path: 'user:id',
    component: EditUserComponent
  }

]

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule { }
