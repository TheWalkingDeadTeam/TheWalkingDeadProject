function download() {
	var dd = {
		content: [
			{
				text: 'Анкета на вступ до курсів у НЦ "NetCracker"',
				style: 'header'
			}
		],
		styles: {
			header: {
				fontSize: 18,
				bold: true,
				alignment: 'center',
				margin: [0, 0, 0, 5]
			},
			plain: {
				fontSize: 12,
				bold: false,
				alignment: 'justify',
				margin: [0, 5]
			}
		}
	};

	var fields = document.getElementById("fields").getElementsByTagName("div");
	for (var i = 0; i < fields.length; i++) {
		var field = fields[i];
		var question = "";
		var answer = "";
		switch (field.id) {
			case "block" + i:
				if (field.getElementsByTagName("textarea").length > 0) {
					question = field.getElementsByTagName("span")[0].textContent;
					answer = field.getElementsByTagName("textarea")[0].value;
				} else if (field.getElementsByTagName("input").length > 0) {
					question = field.getElementsByTagName("label")[0].textContent;
					answer = field.getElementsByTagName("input")[0].value;
				} else if (field.getElementsByTagName("select").length > 0) {
					question = field.getElementsByTagName("span")[0].textContent;
					var slct = field.getElementsByTagName("select")[0];
					var n = slct.options.selectedIndex;
					answer = slct.options[n].text;
				}
				break;
			case "radioBlock" + i:
			case "checkBlock" + i:
				question = field.getElementsByTagName("span")[0].textContent;
				var checkBoxes = field.getElementsByTagName("input");
				for (var j = 0; j < checkBoxes.length; j++) {
					if (checkBoxes[j].checked) {
						answer += field.getElementsByTagName("label")[j].textContent + ", ";
					}
				}
				answer = answer.substring(0, answer.length - 2);
				break;
		}
		dd.content.push({
			text: question + answer,
			style: 'plain'
		});
	}

	pdfMake.createPdf(dd).download();
}