"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
function getBaseUrl() {
    return 'http://145.40.19.61:8080';
}
function copyByName(source, destination) {
    var childnodes = Array.from(source.childNodes);
    childnodes.forEach(function onNode(currentValue) {
        if (currentValue instanceof HTMLElement) {
            if (currentValue.hasAttribute('jfield')) {
                var jfieldvalue = currentValue.getAttribute('jfield');
                if (jfieldvalue === 'value') {
                    var name = currentValue.getAttribute('name');
                    if (currentValue instanceof HTMLTextAreaElement) {
                        destination[name] = currentValue.value;
                    }
                    else if (currentValue instanceof HTMLInputElement) {
                        if (currentValue.hasAttribute('checked')) {
                            destination[name] = currentValue.checked;
                        }
                    }
                    else if (currentValue instanceof HTMLSelectElement) {
                        destination[name] = currentValue[currentValue.selectedIndex].getAttribute('value');
                    }
                }
            }
            if (currentValue.hasChildNodes) {
                copyByName(currentValue, destination);
            }
        }
    });
}
function unionOperationOrg() {
    var res;
    var request = new XMLHttpRequest();
    request.open('GET', getBaseUrl() + '/greeting', true);
    request.send();
    request.onload = function () {
        if (request.readyState == 4 && request.status == 200) {
            try {
                var jsonobjdata = JSON.parse(request.responseText);
                res = jsonobjdata.result;
            }
            catch (error) {
                res = res + "parse error" + error;
            }
            console.log(jsonobjdata);
        }
        else {
            res = res + " WTF!!!!!!!!";
            console.error(request.responseText);
        }
        document.getElementById("list-op-result-text").value = "Hasse -sasas---" + res;
        console.log("apa");
    };
}
function unionOperation(source, destination) {
    var obj = {};
    copyByName(source, obj);
    var request = new XMLHttpRequest();
    request.open('POST', getBaseUrl() + '/list/operation', true);
    request.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    request.send(JSON.stringify(obj));
    request.onload = function () {
        if (request.readyState == 4 && request.status == 200) {
            try {
                var jsonobjdata = JSON.parse(request.responseText);
                destination.value = jsonobjdata.result;
            }
            catch (error) {
            }
        }
        else if (request.status == 500) {
            destination.value = "hasse nu vart det nå fel 500:" + request.response;
        }
        else if (request.status == 400) {
            destination.value = "hasse nu vart det nå fel 400:" + request.response;
        }
        else {
            destination.value = "hasse nu vart det nå fel" + request.response;
        }
    };
}
function simpleGet(requestMapping, pOnload) {
    var request = new XMLHttpRequest();
    request.open('GET', getBaseUrl() + requestMapping, true);
    request.send();
    request.onload = pOnload;
}
function getGreetingOrg(destination) {
    var request = new XMLHttpRequest();
    request.open('GET', getBaseUrl() + '/greeting', true);
    request.send();
    request.onload = function () {
        if (request.readyState == 4 && request.status == 200) {
            try {
                var jsonobjdata = JSON.parse(request.responseText);
                destination.innerHTML = jsonobjdata.result;
            }
            catch (error) {
                console.log(error);
                destination.innerHTML = "Något gick fel";
            }
        }
        else {
            destination.innerHTML = "Något gick fel";
        }
    };
}
function getGreeting(destination) {
    simpleGet('/greeting', function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                var jsonobjdata = JSON.parse(this.responseText);
                destination.innerHTML = jsonobjdata.result;
            }
            catch (error) {
                console.log(error);
                destination.innerHTML = "Något gick fel";
            }
        }
        else {
            destination.innerHTML = "Något gick fel";
        }
    });
}
function getBuss55(l1, l2, l3) {
    simpleGet('/bus55', function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                var busres = JSON.parse(this.responseText);
                l1.innerHTML = busres.avg1;
                l2.innerHTML = busres.avg2;
                l3.innerHTML = busres.avg3;
            }
            catch (error) {
                console.log(error);
                l1.innerHTML = "N/A";
                l2.innerHTML = "N/A";
                l3.innerHTML = "N/A";
            }
        }
        else {
            l1.innerHTML = "N/A";
            l2.innerHTML = "N/A";
            l3.innerHTML = "N/A";
        }
    });
}
function initHideViewSibling(collapsableClassName) {
    var coll = document.getElementsByClassName(collapsableClassName);
    var i;
    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            }
            else {
                content.style.display = "block";
            }
        });
    }
}
function initTree(treeId) {
    var tree = document.getElementById(treeId);
    var toggler = tree.getElementsByClassName("caret");
    var i;
    for (i = 0; i < toggler.length; i++) {
        toggler[i].addEventListener("click", function () {
            this.parentElement.querySelector(".nested").classList.toggle("active");
            this.classList.toggle("caret-down");
        });
    }
}
//# sourceMappingURL=index.js.map