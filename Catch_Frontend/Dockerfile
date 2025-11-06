FROM node:18
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
CMD ["npx", "ng", "serve", "--host", "0.0.0.0", "--port", "80"]