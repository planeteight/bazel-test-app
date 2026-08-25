FROM mcr.microsoft.com/devcontainers/base:ubuntu-24.04

# Core CLI tools that are frequently useful for AI-assisted coding workflows.
RUN apt-get update \
   && export DEBIAN_FRONTEND=noninteractive \
   && apt-get install -y --no-install-recommends \
   build-essential \
   ca-certificates \
   cmake \
   curl \
   fd-find \
   git \
   gradle \
   openjdk-21-jdk \
   jq \
   ripgrep \
   shellcheck \
   shfmt \
   tree \
   unzip \
   wget \
   && ln -sf "$(command -v fdfind)" /usr/local/bin/fd \
   && apt-get clean \
   && rm -rf /var/lib/apt/lists/*
