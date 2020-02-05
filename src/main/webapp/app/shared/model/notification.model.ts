import { NotificationState } from 'app/shared/model/enumerations/notification-state.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';

export interface INotification {
  id?: number;
  notificationTitle?: string;
  notificationSubject?: string;
  notificationUrl?: string;
  notificationImageContentType?: string;
  notificationImage?: any;
  notificationStatus?: NotificationState;
  notificationType?: NotificationType;
  userFirstName?: string;
  userId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public notificationTitle?: string,
    public notificationSubject?: string,
    public notificationUrl?: string,
    public notificationImageContentType?: string,
    public notificationImage?: any,
    public notificationStatus?: NotificationState,
    public notificationType?: NotificationType,
    public userFirstName?: string,
    public userId?: number
  ) {}
}
