import { z } from 'zod';

export const loginSchema = z.object({
  username: z
    .string()
    .min(6, { message: 'The username must be between 6 and 30 characters.' })
    .max(30, { message: 'The username must be between 6 and 30 characters.' }),
  password: z.string().min(8, {
    message: 'Password must be at least 8 characters.',
  }),
});
