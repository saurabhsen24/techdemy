import { Injectable } from '@angular/core';
import Swal, { SweetAlertIcon } from 'sweetalert2';
import { ErrorResponse } from '../models/responses/ErrorResponse.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor() { }

  showErrorMessage(errorResponse: ErrorResponse) {
    Swal.fire({
      icon: "error",
      titleText: `${errorResponse.message}`,
    });
  }

  showMessage(swalIcon: SweetAlertIcon, message: string) {
    Swal.fire({
      icon: swalIcon,
      titleText: `${message}`,
    });
  }

  getToast() {
    const Toast = Swal.mixin({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener("mouseenter", Swal.stopTimer);
        toast.addEventListener("mouseleave", Swal.resumeTimer);
      },
    });

    return Toast;
  }

}
