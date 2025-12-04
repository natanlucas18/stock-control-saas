# ---------------------------
# Base de desenvolvimento
# ---------------------------
FROM node:22-alpine

# Diretório padrão
WORKDIR /app

# Copia apenas arquivos de dependência
COPY package.json package-lock.json ./

# Instala dependências (modo rápido, sem prune)
RUN npm install

# Copia tudo (mas será sobrescrito pelos volumes)
COPY . .

# Expõe porta do Next.js
EXPOSE 3000

# Ativa host reload com turbopack
CMD ["npm", "run", "app"]
