# CI/CD

## 环境准备

- Docker Desktop

- GitLab 本地安装，方便调试

```
version: "3.4"
services:
  gitlab:
    image: 'yrzr/gitlab-ce-arm64v8'
    restart: always
    hostname: 'gitlab.local'
    container_name: gitlab-ce
    privileged: true
    depends_on:
      - postgresql
    links:
     - postgresql:postgresql
    environment:
      GITLAB_OMNIBUS_CONFIG: |
        postgresql['enable'] = true
        gitlab_rails['db_username'] = "gitlab"
        gitlab_rails['db_password'] = "gitlab"
        gitlab_rails['db_host'] = "postgresql"
        gitlab_rails['db_port'] = "5432"
        gitlab_rails['db_database'] = "gitlabhq_production"
        gitlab_rails['db_adapter'] = 'postgresql'
        gitlab_rails['db_encoding'] = 'utf8'
        external_url 'http://gitlab.local'
    ports:
      - "8080:80"
      - "8043:443"
      - "8022:22"
    volumes:
      - ./gitlab-config:/etc/gitlab
      - ./gitlab-logs:/var/log/gitlab
      - ./gitlab-data:/var/opt/gitlab
  postgresql:
    restart: always
    image: postgres:13.6-alpine
    container_name: gitlab-postgres
    environment:
      - POSTGRES_USER=gitlab
      - POSTGRES_PASSWORD=gitlab
      - POSTGRES_DB=gitlabhq_production
      - TZ=America/Bogota
      - PGDATA=/data
    volumes:
      - ./gitlab-db-data:/data
    ports:
      - "5432:5432"



sudo docker exec -it gitlab-ce grep 'Password:' /etc/gitlab/initial_root_password

root
1yM/H4UNqHd1Yxs5+aDMdECXYuGC+vgrO1TKQP3cQ8w=
```

- [argo-workflows 安装](https://argoproj.github.io/argo-workflows/quick-start/)
- [argo-events 安装](https://argoproj.github.io/argo-events/installation/)
- [argo-cd 安装](https://argo-cd.readthedocs.io/en/stable/#quick-start)
- 
