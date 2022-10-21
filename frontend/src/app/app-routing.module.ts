import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './component/admin-dashboard/admin-dashboard.component';
import { CartComponent } from './component/cart/cart.component';
import { CourseComponent } from './component/course/course.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { EnrollmentComponent } from './component/enrollment/enrollment.component';
import { ForgetPasswordComponent } from './component/forget-password/forget-password.component';
import { LoginComponent } from './component/login/login.component';
import { ResetPasswordComponent } from './component/reset-password/reset-password.component';
import { SignupComponent } from './component/signup/signup.component';
import { AdminAuthGuard } from './guards/admin-auth.guard';
import { AuthGuard } from './guards/auth.guard';
import { UserAuthGuard } from './guards/user-auth.guard';

const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate: [UserAuthGuard] },
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [AdminAuthGuard],
  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: SignupComponent },
  { path: 'forgetPassword', component: ForgetPasswordComponent },
  { path: 'resetPassword', component: ResetPasswordComponent },
  { path: 'cart', component: CartComponent, canActivate: [UserAuthGuard] },
  {
    path: 'enrollments',
    component: EnrollmentComponent,
    canActivate: [UserAuthGuard],
  },
  {
    path: 'course/:courseId',
    component: CourseComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
