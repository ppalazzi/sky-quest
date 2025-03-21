export function spinWord(words: string): string {
	return words.split(' ').map(word => word.length < 5 ? word : reverseString(word)).join(' ');
}

function reverseString(word: string): string {
	return word.split('').reverse().join('');
}

function getCount(str: string): number {
	return str.split('').filter(char => isVowel(char)).length;
}

function isVowel(value: string): boolean {
	return value === 'a' || value === 'e' || value === 'i' || value === 'o' || value === 'u';
}

function digPow (n: number, p: number): number {
	const strings = n.toString().split('');
	const value = strings.reduce(
		(acc, char, index) => {
			return acc + Math.pow(parseInt(char), p + index);
		},
		0
	);

	if (value % n === 0) {
		return value / n ;
	}

	return -1;
}

export function solution(str: string, ending: string): boolean {
	return str.endsWith(ending);
}

export function maskify(cc: string): string {
	if (cc.length <= 4) return cc;
	return cc.split('').map((char, index) => index < cc.length - 4 ? '#' : char).join('')
}

console.log(maskify('4556364607935616'));
