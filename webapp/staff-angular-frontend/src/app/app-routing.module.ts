import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {UserListComponent} from "./components/user-list/user-list.component";
import {AccountListComponent} from "./components/account-list/account-list.component";
import {EditUserComponent} from "./components/edit-user/edit-user.component";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {EditAccountComponent} from "./components/edit-account/edit-account.component";
import {AddUserComponent} from "./components/add-user/add-user.component";
import {ChangeAccountPasswordComponent} from "./components/change-account-password/change-account-password.component";
import {AccountResolver} from "./guards/account-resolver";
import {UserResolver} from "./guards/user-resolver";

const routes: Routes = [
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  {path: "dashboard", component: DashboardComponent},
  {path: 'users', component: UserListComponent},
  {path: 'accounts', component: AccountListComponent},
  {path: 'users/new', component: AddUserComponent},
  {path: 'users/:id', component: EditUserComponent, resolve: {user: UserResolver}},
  {path: 'accounts/:id', component: EditAccountComponent, resolve: {account: AccountResolver}},
  {path: 'accounts/:id/changePassword', component: ChangeAccountPasswordComponent}
];

@NgModule({
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forRoot(routes)
  ]
})
export class AppRoutingModule {
}
