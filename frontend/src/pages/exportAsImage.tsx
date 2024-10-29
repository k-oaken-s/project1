import html2canvas from 'html2canvas';

// ランク表作成ページまたは共有ページ内で
const exportAsImage = async () => {
  const element = document.getElementById('ranking-table');
  if (element) {
    const canvas = await html2canvas(element);
    const link = document.createElement('a');
    link.href = canvas.toDataURL('image/png');
    link.download = 'ranking.png';
    link.click();
  }
};
