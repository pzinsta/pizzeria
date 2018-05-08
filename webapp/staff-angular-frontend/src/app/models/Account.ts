import {User} from "./User";

export interface Account {
  id: number;
  username: string;
  enabled: boolean;
  accountExpired: boolean;
  credentialsExpired: boolean;
  accountLocked: boolean;
  createdOn: Date;
  user: User;
}
