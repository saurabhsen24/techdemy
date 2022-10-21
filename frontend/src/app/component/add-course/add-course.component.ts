import { Component, OnInit } from '@angular/core';

declare var $: any;

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css'],
})
export class AddCourseComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  showCreateCourseModal() {
    $('#addCourseModal').modal('show');
  }
}
