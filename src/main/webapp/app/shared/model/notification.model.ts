import { NotificationState } from 'app/shared/model/enumerations/notification-state.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  title?: string;
  subject?: string;
  url?: string;
  imageContentType?: string;
  image?: any;
  status?: NotificationState;
  typeNotif?: NotificationType;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public title?: string,
    public subject?: string,
    public url?: string,
    public imageContentType?: string,
    public image?: any,
    public status?: NotificationState,
    public typeNotif?: NotificationType
  ) {}
}
