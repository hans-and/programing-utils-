function copyByName(source, destinationObj) {
    var childnodes = source.childNodes;
    childnodes.forEach(function onNode(currentValue) {
        if (currentValue instanceof HTMLElement) {
            if (currentValue.hasAttribute('jfield')) {
                var jfieldvalue = currentValue.getAttribute('jfield');
                if (jfieldvalue === 'value') {
                    var name = currentValue.getAttribute('name');
                    if (name != null) {
                        if (currentValue instanceof HTMLTextAreaElement) {
                            destinationObj[name] = currentValue.value;
                        }
                        else if (currentValue instanceof HTMLInputElement) {
                            if (currentValue.hasAttribute('checked')) {
                                destinationObj[name] = currentValue.checked;
                            }
                        }
                        else if (currentValue instanceof HTMLSelectElement) {
                            destinationObj[name] = currentValue[currentValue.selectedIndex].getAttribute('value');
                        }
                    }
                }
            }
            if (currentValue.hasChildNodes()) {
                copyByName(currentValue, destinationObj);
            }
        }
    });
}
function unionOperation(source, destination) {
    var obj = {};
    copyByName(source, obj);
    simplePost('list/operation', obj, function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                destination.value = JSON.parse(this.responseText).result;
            }
            catch (error) {
                destination.value = "Ja jo go ipa" + error;
            }
        }
        else if (this.status == 500) {
            destination.value = "hasse nu vart det nå fel 500:" + this.response;
        }
        else if (this.status == 400) {
            destination.value = "hasse nu vart det nå fel 400:" + this.response;
        }
        else if (this.status == 415) {
            destination.value = "hasse nu vart det nå fel 415:" + this.response + "sent: " + JSON.stringify({ "operation": "UNION", "ignoreCase": true, "sort": true, "trim": true, "listA": "mjau", "listB": "assssaaa" });
        }
        else {
            destination.value = "hasse nu vart det nå fel" + this.response + this.status + '  obj:' + obj;
        }
    });
}
function getBaseUrl() {
    return "https://hasses-magical.club/";
}
function simpleGet(requestMapping, pOnload) {
    var request = new XMLHttpRequest();
    request.open('GET', getBaseUrl() + requestMapping, true);
    request.send();
    request.onload = pOnload;
}
function simplePost(requestMapping, obj, pOnload) {
    var request = new XMLHttpRequest();
    request.open('Post', getBaseUrl() + requestMapping, true);
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify(obj));
    request.onload = pOnload;
}
function getGreeting(destination) {
    simpleGet('lastCalled', function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                var jsonobjdata = JSON.parse(this.responseText);
                if (jsonobjdata.result != null) {
                    destination.innerHTML = jsonobjdata.result;
                }
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
    simpleGet('bus55', function () {
        if (this.readyState == 4 && this.status == 200) {
            try {
                var busres = JSON.parse(this.responseText);
                if (busres.avg1 != null && busres.avg2 != null && busres.avg3 != null) {
                    l1.innerHTML = busres.avg1;
                    l2.innerHTML = busres.avg2;
                    l3.innerHTML = busres.avg3;
                }
            }
            catch (error) {
                console.log(error);
                l1.innerHTML = "N/A";
                l2.innerHTML = "N/A";
                l3.innerHTML = "N/A";
            }
        }
        else {
            l1.innerHTML = this.responseText;
            l2.innerHTML = "N/A";
            l3.innerHTML = "N/A";
        }
    });
}
function initHideViewSibling(collapsableClassName) {
    var coll = document.getElementsByClassName(collapsableClassName);
    var i;
    for (i = 0; i < coll.length; i++) {
        var collapseitem = coll[i];
        collapseitem.addEventListener("click", function collapse() {
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
