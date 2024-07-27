#base image
FROM node:18 as build-stage

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build --prod

#Second base image les sever nginx

FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build-stage /app/dist/front-end-book /usr/share/nginx/html

EXPOSE 80
#le port par defaut de nginx

#to run this file we should run the following command
# 1 entre dans le fichier de frontend book-network and after that we should run the commande
# 2 docker build -t bns/bns-ui:1.0.0 -f ../Docker/frontEnd/Dockerfile .