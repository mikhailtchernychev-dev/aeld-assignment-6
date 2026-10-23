# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# Unable to find any files that looked like license statements. Check the accompanying
# documentation and source headers and set LICENSE and LIC_FILES_CHKSUM accordingly.
#
# NOTE: LICENSE is being set to "CLOSED" to allow you to at least start building - if
# this is not accurate with respect to the licensing of the software being built (it
# will not be in most cases) you must specify the correct value before using this
# recipe for anything other than initial testing/development!
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# No information for SRC_URI yet (only an external source tree was specified)
#SRC_URI = ""

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-mikhailtchernychev-dev.git;protocol=ssh;branch=main"


PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "359eeff0f801ba6d7aa0f18adcb8fdb94b0b1bd6"

# This sets your staging directory based on WORKDIR, where WORKDIR is defined at 
# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-WORKDIR
# We reference the "server" directory here to build from the "server" directory
# in your assignments repo
S = "${WORKDIR}/git/aesd-char-driver"


inherit module

#MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

#MODULES_MODULE_SYMVERS_LOCATION:append = "misc-modules"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"

# below for external scr in a folder
#inherit externalsrc
#EXTERNALSRC = "/data/coursera-linux/assignment-1-mikhailtchernychev-dev/aesd-char-driver"
#EXTERNALSRC_BUILD = "/data/coursera-linux/assignment-1-mikhailtchernychev-dev/aesd-char-driver"

do_install:append() {
    #install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/updates
    #install -m 0644 ${S}/aesdchar.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/updates
    install -d ${D}${bindir} 
    install -m 0755 ${S}/aesdchar_load ${D}${bindir}/aesdchar_load
    install -m 0755 ${S}/aesdchar_unload ${D}${bindir}/aesdchar_unload  
}

# Ensure the script file is packaged into the main package (${PN})
FILES:${PN} += "${bindir}/aesdchar_load \
                ${bindir}/aesdchar_unload \
                "