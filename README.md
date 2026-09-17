# meta-bolt-native-player-service

A bitbake meta layer for building a Firebolt **bolt** application.

This layer builds a native player bolt package. 
(`recipes-extended/nativeplayer/`) 

## Repository layout

```
conf/                         layer.conf and conf-notes for this meta layer
manifests/deps.xml            repo manifest of build dependencies (meta-bolt-distro)
package-configs/              bolt package metadata (com.rdkcentral.nativeplayer.json,  nativeplayer.bolt.json)
recipes-core/images/          the *-bolt-image image recipe
recipes-extended/             nativeplayer and supporting recipes 
recipes-multimedia/           Third party opensource components
setup-environment, repo-sync  standalone build harness
```
## Creating your application from this repo

### Setup and building

Build-host prerequisites are the same as for the distro — see
[Setup and building](https://github.com/rdkcentral/meta-bolt-distro/blob/develop/README.md#setup-and-building)
in the [meta-bolt-distro](https://github.com/rdkcentral/meta-bolt-distro)
documentation.

* Clone your application repository and enter its root directory (which is the
  layer root).
```
git clone https://github.com/rdkcentral/meta-bolt-native-player-service
cd meta-bolt-native-player-service
```

* Set up the build environment. This fetches `meta-bolt-distro` into `deps/bolt`
  (via `repo-sync`) and configures the build.
```
source setup-environment
```

### Building the application as a bolt package

This is the standard way of building the application. The
[bolt tool](https://github.com/rdkcentral/bolt-tools/tree/main/bolt)'s
[bolt make](https://github.com/rdkcentral/bolt-tools/blob/main/bolt/docs/make.md)
command builds the OCI image itself as part of creating the package, so there
is no need to build it separately beforehand.

The bolt tool and the tools it depends on do not have to be preinstalled on the
build host — if any of them are missing, a single command compiles them all and
sets up the bolt tool, making them available on the PATH of the build
environment:
```
bitbake bolt-env
```

With the build environment set up (`source setup-environment`, see above),
ensure the base package is available in the
[local package store](https://github.com/rdkcentral/bolt-tools/blob/main/bolt/docs/local-package-store.md) —
either download it
with [bolt fetch](https://github.com/rdkcentral/bolt-tools/blob/main/bolt/docs/fetch.md)
or build it yourself as described in
[Building the base bolt package](https://github.com/rdkcentral/meta-bolt-distro?tab=readme-ov-file#building-the-base-bolt-package) —
then run:
```
bolt make nativeplayer
```

### Building only the OCI image

If you want the OCI image without creating a bolt package, build it directly
with bitbake (also requires the build environment to be set up):
```
bitbake nativeplayer-bolt-image             # no multi config

bitbake mc:arm:nativeplayer-bolt-image   \
        mc:arm64:nativeplayer-bolt-image \
        mc:amd64:nativeplayer-bolt-image    # requires multi config
```

## Running the bolt package on a device

Use `bolt push` and `bolt run` as described in their help pages —
[bolt push](https://github.com/rdkcentral/bolt-tools/blob/main/bolt/docs/push.md) and
[bolt run](https://github.com/rdkcentral/bolt-tools/blob/main/bolt/docs/run.md).
`<remote>` is the hostname or alias of a device reachable over SSH in
non-interactive mode.
```
bolt push <remote> com.rdkcentral.base+0.3.1
bolt push <remote> com.rdkcentral.nativeplayer+*

bolt run  <remote> com.rdkcentral.nativeplayer+*
```


## Pinning dependencies

`manifests/deps.xml` controls which version of `meta-bolt-distro` (and any other
repositories you add) is fetched. `revision` must be a **tag**
(`refs/tags/<tag>`) or a **commit SHA**, never a branch, to keep builds
reproducible. For releases, use the distro release tag matching the base layer
version the app depends on (see `dependencies` in
`package-configs/com.rdkcentral.nativeplayer.json`), for example:
```xml
<project remote="rdkcentral" name="meta-bolt-distro"
         upstream="main" revision="refs/tags/0.2.0" path="bolt"/>
```
During development, a revision of a development version may be used as well,
for example a commit SHA on the `develop` branch:
```xml
<project remote="rdkcentral" name="meta-bolt-distro"
         upstream="develop" revision="<commit SHA>" path="bolt"/>
```
Add any extra meta-layer repositories your recipes need as further `<project>`
entries.
