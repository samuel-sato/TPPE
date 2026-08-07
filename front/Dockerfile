####
# This Dockerfile packages the already-built Angular production bundle and
# serves it with nginx.
#
# It does NOT run `npm install` / `ng build` inside the image: the pipeline
# (.github/workflows/frontend.yml) builds the app first (job "Build") and
# downloads the compiled artifact into front/dist/front before this image is
# built, the same convention used by backend/src/main/docker/Dockerfile.jvm.
#
# Before building the image locally run:
#
#   npm ci
#   npm run build
#
# Then build the image with:
#
#   docker build -f Dockerfile -t front .
#
# Then run the container using:
#
#   docker run -i --rm -p 8080:8080 front
#
###

FROM nginxinc/nginx-unprivileged:1.27-alpine

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY dist/front/browser/ /usr/share/nginx/html/

EXPOSE 8080
