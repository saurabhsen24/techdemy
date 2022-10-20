export interface CourseResponse {
  courseId: Number;
  author: string;
  category: string;
  courseDescription: string;
  courseImage: string;
  courseName: string;
  coursePrice: Number;
  courseRating: Number;
  addedToCart?: boolean;
  enrolled?: boolean;
}
