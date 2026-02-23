import { HttpInterceptorFn } from '@angular/common/http';
import { v4 as uuidv4 } from 'uuid';

/** Adds X-Correlation-Id header to every outgoing request. */
export const correlationInterceptor: HttpInterceptorFn = (req, next) => {
  const cloned = req.clone({
    setHeaders: { 'X-Correlation-Id': uuidv4() }
  });
  return next(cloned);
};

