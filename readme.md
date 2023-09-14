before run project open terminal : 
1. pull images: docker pull landoop/fast-data-dev
2. build images :
docker run -e ADV_HOST=127.0.0.1 -e SAMPLEDATA=0 -e RUNTESTS=0 \
-p 3030:3030 -p 9092:9092 --rm landoop/fast-data-dev:latest