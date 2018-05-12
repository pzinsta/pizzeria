import {User} from "./User";
import {Role} from "./Role";

export interface Account {
  id?: number;
  username?: string;
  password?: string;
  enabled?: boolean;
  accountExpired?: boolean;
  credentialsExpired?: boolean;
  accountLocked?: boolean;
  createdOn?: Date;
  user?: User;
  roles?: Role[]
}
