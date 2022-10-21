import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon } from 'sweetalert2';
import { ErrorResponse } from '../models/responses/ErrorResponse.model';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  constructor() {}

  showErrorMessage(errorResponse: ErrorResponse) {
    Swal.fire({
      icon: 'error',
      titleText: `${errorResponse.message}`,
      background: '#000',
      color: '#fff',
    });
  }

  showMessage(swalIcon: SweetAlertIcon, message: string) {
    Swal.fire({
      icon: swalIcon,
      titleText: `${message}`,
      background: '#000',
      color: '#fff',
    });
  }

  getToast() {
    const Toast = Swal.mixin({
      toast: true,
      position: 'top-end',
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });

    return Toast;
  }

  showToastMessage(swalIcon: SweetAlertIcon, message: string) {
    const toast = this.getToast();
    toast.fire({
      icon: swalIcon,
      title: message,
      background: '#000',
      color: '#fff',
    });
  }
}
