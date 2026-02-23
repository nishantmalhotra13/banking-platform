FROM node:20-alpine AS build
WORKDIR /app
COPY banking-ui/package*.json ./
RUN npm ci
COPY banking-ui/ ./
RUN npx ng build --configuration=production

FROM nginx:alpine
COPY --from=build /app/dist/banking-ui/browser /usr/share/nginx/html
COPY banking-ui/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

