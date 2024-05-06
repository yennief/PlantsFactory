FROM node:20-alpine
WORKDIR /app
COPY . .

RUN npm install
ENV REACT_APP_BACKEND_HOST=localhost:8085
RUN npm run build

FROM nginx:latest
COPY --from=0 /app/build/ /usr/share/nginx/html
COPY ./nginx/nginx.conf /etc/nginx/nginx.conf