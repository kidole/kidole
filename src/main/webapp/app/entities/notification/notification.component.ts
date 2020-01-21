import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { NotificationDeleteDialogComponent } from './notification-delete-dialog.component';

@Component({
  selector: 'jhi-notification',
  templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit, OnDestroy {
  notifications?: INotification[];
  eventSubscriber?: Subscription;

  constructor(
    protected notificationService: NotificationService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.notificationService.query().subscribe((res: HttpResponse<INotification[]>) => {
      this.notifications = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNotifications();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INotification): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInNotifications(): void {
    this.eventSubscriber = this.eventManager.subscribe('notificationListModification', () => this.loadAll());
  }

  delete(notification: INotification): void {
    const modalRef = this.modalService.open(NotificationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.notification = notification;
  }
}
