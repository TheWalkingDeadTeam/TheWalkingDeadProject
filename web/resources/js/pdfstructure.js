function createStructure(dataArray, studentPhoto) {
	var dd = {
		content: [
			{ 
				text: 'Анкета на вступ до курсів у НЦ "NetCracker"', 
				style: 'header'
			},
			{
				image: 'photo',
				width: 200,
				alignment: 'right'
			},
			{ 
				text: [
					'Фото\n'
				],
				style: 'plain',
				alignment: 'right'
			}
		],
		styles: {
			header: {
				fontSize: 18,
				bold: true,
				alignment: 'center'
			},
			plain: {
				fontSize: 12,
				bold: false,
				alignment: 'justify'
			}
		},
		images: {
			photo: ''
		}
	}
	for (var i = 0; i < dataArray.length; i++) {
		dd.content.push(dataArray[i]);
	}
	dd.images.photo = studentPhoto;
	return dd;
}