export interface JwtPayload{
    upn: string;    // Corresponde a .upn(userName)
    groups: string; // Corresponde a .groups(roleNum.toString())
    exp?: number;   // Padrão para tempo de expiração (Unix timestamp em segundos)
    iat?: number;   // Padrão para tempo de emissão (Unix timestamp em segundos)
    iss?: string;
}