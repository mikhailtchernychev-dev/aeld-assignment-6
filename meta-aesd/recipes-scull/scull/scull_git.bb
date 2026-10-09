# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/mikhailtchernychev-dev/aeld-assignment-7;protocol=https;branch=main \
           file://0001-preparing-for-yocto-build.patch \
           file://module_load \
           file://module_unload \
           file://scull_load \
           file://scull_unload \
           file://lddmodules \
           "

# Modify these as desired
PV = "1.0+git"
SRCREV = "9b0623facd6396c1c242e15d3a9814041c12fc38"

S = "${WORKDIR}/git"

inherit module
MODULES_MODULE_SYMVERS_LOCATION:append = "scull"

# Inherit the update-rc.d class for startup script integration
inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "lddmodules"
INITSCRIPT_PARAMS = "defaults 98"

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

do_install:append() {
    #install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/updates
    #install -m 0644 ${S}/scull/scull.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/updates

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/module_unload ${D}${bindir}/module_unload
    install -m 0755 ${WORKDIR}/module_load ${D}${bindir}/module_load
    install -m 0755 ${WORKDIR}/scull_load ${D}${bindir}/scull_load
    install -m 0755 ${WORKDIR}/scull_unload ${D}${bindir}/scull_unload  

    # Create the /etc/init.d directory inside the image destination
    install -d ${D}${INIT_D_DIR}
    
    # Install the script with executable permissions (0755)
    install -m 0755 ${WORKDIR}/lddmodules ${D}${INIT_D_DIR}/lddmodules

}

# Ensure the script file is packaged into the main package (${PN})
FILES:${PN} += "${bindir}/module_load \
                ${bindir}/module_unload \
                ${bindir}/scull_load  \
                ${bindir}/scull_unload \
                ${INIT_D_DIR}/lddmodules \
"
